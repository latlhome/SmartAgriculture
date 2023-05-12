import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

// @RunWith(SpringJUnit4ClassRunner.class)
// @SpringBootTest
// @WebAppConfiguration
public class main {
    private static String modelPcnPath = "C:\\Users\\22328\\Desktop\\学习杂物堆\\软件杯\\onnx\\model.onnx";

    public static void main(String[] args) {
        onnxTest onnxTest = new onnxTest(modelPcnPath,1);
    }
    // @Test
    // public void run(){
    //     onnxTest onnxTest = new onnxTest(modelPcnPath,1);
    // }
}
