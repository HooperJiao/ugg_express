import os
import re
import sys
import json
from pdfminer.high_level import extract_text


file_path = "src/test/resources/[4]01:09:2025 – 07:09:2025.pdf"
#file_path = "src/test/resources/[5]08:09:2025 – 14:09:2025.pdf"


# 提取文本
text = extract_text(file_path)

lines = text.split('\n')

print("===== 提取的原始文本（含行号） =====")
for idx, line in enumerate(lines):
    print(f"{idx:02d}: {repr(line)}")
print("=================================")

result = {}

# file_name 和 file_path
result['fileName'] = os.path.basename(file_path)
result['filePath'] = file_path  # 或者换成相对路径

# 拆分行 & 创建迭代器
lines = text.split('\n')
result = {}

# 定义字段映射：key -> 位置偏移（以 hours 为基准）
pay_types = [
    ('ordinaryHours', 9, 13, 16, 19),
    ('after6pmHours', 10, 14, 17, 20),
    ('ordinarySatHours', 11, 15, 18, 21),
]

for key_prefix, i1, i2, i3, i4 in pay_types:
    try:
        result[f"{key_prefix}"] = lines[i1]
        result[f"{key_prefix}HourlyRate"] = lines[i2].replace('$', '')
        result[f"{key_prefix}ThisPay"] = lines[i3].replace('$', '')
        result[f"{key_prefix}YTD"] = lines[i4].replace('$', '').replace(',', '')
    except IndexError:
        print(f"❗ IndexError for {key_prefix}, 检查lines是否长度不够")

# 打印结果
print("✅ 提取结果：啥啊")
print(result)