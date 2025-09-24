import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.hooper.ugg.entity.Payslip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class) // 如果你用的是 JUnit4，否则不需要
@SpringBootTest(classes = com.hooper.ugg.UggExpressApplication.class) //手动指定启动类（主配置类）给 @SpringBootTest
public class TestPthon2 {

    private static String pdfPath = "src/test/resources/[5]08:09:2025 – 14:09:2025.pdf";

    @Test
    public void testPythonParse2() throws Exception {
        String pythonScriptPath = "python/test.py";

        ProcessBuilder pb = new ProcessBuilder("python3", pythonScriptPath, pdfPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

        String line;
        String jsonLine = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (line.startsWith("{") && line.endsWith("}")) {
                jsonLine = line;
            }
        }

        if (jsonLine == null) {
            throw new RuntimeException("未从 Python 输出中找到 JSON 数据！");
        }
    }

    @Test
    public void testPythonParse() throws Exception {
        String pythonScriptPath = "python/parse_payslip.py";

        ProcessBuilder pb = new ProcessBuilder("python3", pythonScriptPath, pdfPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));

        String line;
        String jsonLine = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            if (line.startsWith("{") && line.endsWith("}")) {
                jsonLine = line;
            }
        }

        if (jsonLine == null) {
            throw new RuntimeException("未从 Python 输出中找到 JSON 数据！");
        }

        ObjectMapper mapper = new ObjectMapper();
        // ✅ 自定义 LocalDate 格式为 dd/MM/yyyy
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        mapper.registerModule(javaTimeModule);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Payslip paylist = mapper.readValue(jsonLine, Payslip.class);
        System.out.printf(paylist.toString());
    }
}
