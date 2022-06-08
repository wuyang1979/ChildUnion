package com.qinzi123.controller.micro;

import com.itextpdf.text.pdf.*;
import com.qinzi123.happiness.util.StringUtil;
import com.qinzi123.service.CertificateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @title: CertificateController
 * @package: com.qinzi123.controller.micro
 * @projectName: trunk
 * @description: 成长GO
 * @author: jie.yuan
 * @date: 2022/3/16 0008 14:10
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@RestController
@Api(value = "证书")
public class CertificateController {

    /**
     * dpi越大转换后越清晰，相对转换速度越慢
     */
    private static final Integer DPI = 200;

    /**
     * 转换后的图片类型
     */
    private static final String IMG_TYPE = "jpg";

    @Autowired
    private CertificateService certificateService;

    @RequestMapping(value = "/certificate/getCertificateList", method = RequestMethod.POST)
    private List<LinkedHashMap> getCertificateList(@RequestBody Map map) {
        int start = Integer.parseInt(map.get("start").toString());
        int num = Integer.parseInt(map.get("num").toString());
        String userId = map.get("userId").toString();
        List<LinkedHashMap> certificateList = certificateService.getCertificateList(userId, start, num);
        certificateList.forEach(item -> {
            Map<String, Object> productMap = certificateService.getProductInfoByProductId(item);
            item.put("main_image", productMap.get("main_image"));
            item.put("activity_name", productMap.get("name"));
        });
        return certificateList;
    }

    @RequestMapping(value = "/certificate/getChildrenNumById", method = RequestMethod.POST)
    private int getChildrenNumById(@RequestBody Map map) {
        return certificateService.getChildrenNumById(map);
    }

    @ApiOperation(value = "生成证书", notes = "生成证书")
    @RequestMapping(value = "/certificate/generateCertificate/{name}-{certificateId}", method = RequestMethod.GET)
    public void generateCertificate(@PathVariable("name") String name,
                                    @PathVariable("certificateId") String certificateId,
                                    HttpServletResponse response) throws Exception {

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
            fillData(fields, data(name, certificateId));

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
    public void fillData(AcroFields fields, Map<String, String> data) throws IOException, DocumentException, com.itextpdf.text.DocumentException {
        for (String key : data.keySet()) {
            String value = data.get(key); // 调用data方法获取值
            fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
        }
    }

    // 为需要填入的数据value赋值
    public Map<String, String> data(String name, String certificateId) {
        Map<String, String> data = new HashMap<String, String>();
        int id = Integer.parseInt(certificateId);
        Map<String, Object> certificateMap = certificateService.getCertificateInfoById(id);
        String certificateTime = certificateMap.get("certificate_time").toString();
        if (StringUtils.isNotEmpty(certificateTime)) {
            String[] certificateArr = certificateTime.split("-");
            certificateTime = certificateArr[0] + "年" + certificateArr[1] + "月" + certificateArr[2] + "日";
        }
        // 字段需要对应pdf模板里面的名称
        data.put("name", name);
        data.put("activityName", certificateService.getActivityNameById(certificateMap));
        //机构简称
        data.put("institutionName", certificateService.getInstitutionNameById(certificateMap));
        data.put("time", certificateTime);
        return data;
    }
}
