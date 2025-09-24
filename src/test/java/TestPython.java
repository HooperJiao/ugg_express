import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TestPython {

    @Test
    public void testParsePaylistPDF() throws Exception {
        // 获取测试 PDF 的绝对路径
        File file = new File("src/test/resources/[5]08:09:2025 – 14:09:2025.pdf");
        if (!file.exists()) {
            throw new FileNotFoundException("测试文件不存在: " + file.getAbsolutePath());
        }

        String pdfPath = file.getAbsolutePath();
        String pythonScriptPath = "python/parse_payslip.py"; // 相对项目路径

        // 构建 ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(
                "python3",
                pythonScriptPath,
                pdfPath
        );

        pb.redirectErrorStream(true); // 合并标准输出与错误输出
        Process process = pb.start();

        // 读取输出
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
        String line;
        String jsonLine = null;

        while ((line = reader.readLine()) != null) {
            System.out.println(line); // 打印全部输出
            if (line.startsWith("{") && line.endsWith("}")) {
                jsonLine = line; // 记录最后一个 JSON 行
            }
        }

        if (jsonLine == null) {
            throw new RuntimeException("未从 Python 输出中找到 JSON 数据！");
        }

        // 解析 JSON
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> result = objectMapper.readValue(jsonLine, Map.class);

        // 简单断言示例（可根据你实际返回的字段来做）
        //assert result.containsKey("payPeriodStart");
        //assert result.containsKey("payPeriodEnd");

        System.out.println("解析结果：" + result);
    }
}