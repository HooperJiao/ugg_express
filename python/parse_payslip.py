import os
import re
import sys
import json
from pdfminer.high_level import extract_text

def parse_pdf(file_path):
    # 提取文本
    text = extract_text(file_path)

    result = {}

    # 拆分行 & 创建迭代器
    lines = text.split('\n')
    lines_iter = iter(lines)

    # 分区：用来区分不同的 TOTAL 行，以便注入
    current_section = None

    # 横竖交叉读，硬编码
    result['realName'] = lines[14]

    result['bankRef'] = lines[100]
    result['referenceName'] = lines[102]
    result['payAmount'] = lines[110].replace('$', '').replace(',', '')

    result['grossPay'] = lines[74].replace('$', '').replace(',', '')
    result['grossPayYtd'] = lines[76].replace('$', '').replace(',', '')

    result['tax'] = lines[78].replace('$', '').replace(',', '')
    result['taxYtd'] = lines[80].replace('$', '').replace(',', '')

    result['superannuation'] = lines[88].replace('$', '').replace(',', '')
    result['superYtd'] = lines[90].replace('$', '').replace(',', '')


    for line in lines_iter:
        line = line.strip()
        if "Pay Period" in line:
            period = line.split(":")[-1].strip()
            if "-" in period:
                start, end = period.split("-")
                result['payPeriodStart'] = start.strip()
                result['payPeriodEnd'] = end.strip()
        elif "Payment Date" in line:
            result['paymentDate'] = line.split(":")[-1].strip()
        elif "Total Earnings" in line:
            result['totalEarnings'] = line.split(":")[-1].strip().replace('$', '')
        elif "Net Pay" in line:
            result['netPay'] = line.split(":")[-1].strip().replace('$', '')

    extract_fixed_pay_info(lines, result)
    return result

def extract_fixed_pay_info(lines: list[str], result: dict):
    # 定义工资类型及其字段偏移量
    fixed_offsets = {
        "ordinaryHours": [46, 54, 60, 66],
        "after6pmHours": [48, 56, 62, 68],
        "satHours": [50, 58, 64, 70]
    }

    for key_prefix, offsets in fixed_offsets.items():
        try:
            result[f"{key_prefix}"] = clean(lines[offsets[0]])
            result[f"{key_prefix}HourlyRate"] = clean(lines[offsets[1]])
            result[f"{key_prefix}ThisPay"] = clean(lines[offsets[2]])
            result[f"{key_prefix}Ytd"] = clean(lines[offsets[3]])
        except IndexError:
            print(f"⚠️ Warning: Offset out of range for {key_prefix}")
            continue

def clean(value: str):
    """去除 $, , 和多余空格等标记"""
    return value.replace('$', '').replace(',', '').strip()

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python parse_payslip.py <pdf_path>")
        sys.exit(1)

    pdf_path = sys.argv[1]
    data = parse_pdf(pdf_path)

    # 输出 JSON（供 Java 捕获）
    print(json.dumps(data, ensure_ascii=False))