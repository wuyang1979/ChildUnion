package com.qinzi123.controller.micro;

import com.itextpdf.text.pdf.*;
import com.qinzi123.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * @title: Test
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description:
 * @author: jie.yuan
 * @date: 2022/3/2 0002 16:49
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "测试")
public class Test {

    /**
     * dpi越大转换后越清晰，相对转换速度越慢
     */
    private static final Integer DPI = 200;

    /**
     * 转换后的图片类型
     */
    private static final String IMG_TYPE = "jpg";

    @ApiOperation(value = "生成证书", notes = "生成证书")
    @RequestMapping(value = "/test/generateCertificate", method = RequestMethod.GET)
    public void generateCertificate(HttpServletResponse response) throws Exception {

        /* 导入PDF模板 */
        String fileName = "https://www.qinzi123.com/file/pdf/zhengshu.pdf";

        try {
            PdfReader reader = new PdfReader(fileName);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            /* 2.读取PDF模板内容 */
            PdfStamper ps = new PdfStamper(reader, bos);
            PdfContentByte under = ps.getUnderContent(1);

            /* 3.法1:设置使用itext-asian.jar的中文字体 */
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //法2:使用项目下的自定义的中文字体
//            BaseFont bf = BaseFont.createFont("./simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            //法3:使用windows系统下的字体库
            //bfChinese = BaseFont.createFont("c://windows//fonts//simsun.ttc,1",BaseFont.IDENTITY_H, false);

            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
            fontList.add(bf);

            /* 4.获取模板中的所有字段 */
            AcroFields fields = ps.getAcroFields();
            fields.setSubstitutionFonts(fontList);

            //调用方法执行写入
            fillData(fields, data());

            /* 必须要调用这个，否则文档不会生成的 */
            ps.setFormFlattening(true);
            ps.close();


            byte[] fileContent = bos.toByteArray();
            byte[] result;
            try (PDDocument document = PDDocument.load(fileContent)) {
                PDFRenderer renderer = new PDFRenderer(document);
                BufferedImage bufferedImage = renderer.renderImageWithDPI(0, DPI);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, IMG_TYPE, out);
                result = out.toByteArray();
            }

            OutputStream fos = response.getOutputStream();
            fos.write(result);
            fos.flush();
            fos.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取pdf模板中有哪些字段key+赋值的值value
    public static void fillData(AcroFields fields, Map<String, String> data) throws IOException, DocumentException, com.itextpdf.text.DocumentException {
        for (String key : data.keySet()) {
            String value = data.get(key); // 调用data方法获取值
            System.out.println(key + "字段:" + value);
            fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
        }
    }

    // 为需要填入的数据value赋值
    public static Map<String, String> data() {
        Map<String, String> data = new HashMap<String, String>();
        // 字段需要对应pdf模板里面的名称
        data.put("name", "邹tao畀瘬2");
        data.put("activityName", "邹tao畀瘬2");
        data.put("institutionName", "邹tao畀瘬2");
        data.put("time", "2018年7月22日");
        return data;
    }
}
