package com.xuying;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.xml.sax.SAXException;

import com.xuying.util.PDFLayoutTextStripper;



public class Test {
	public static void main(String[] args) throws Exception, InterruptedException, OpenXML4JException, ParserConfigurationException, SAXException {
//		File file = new File("C:/Users/xuyin/Desktop/地图工单导入模板.xlsx");  
//        readExcel(file);  
//        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]+"); 
//        Matcher isNum = pattern.matcher("555");
//        System.out.println(isNum.matches());

		getTextFromPDF("C:/Users/xuyin/Desktop/ceshi1.pdf");
//		getTextFromPDF("C:/cxy/java_develop/总结/10.1的礼物/JavaEE学习笔记.pdf");
//		getTextFromWord("C:/Users/xuyin/Desktop/1.docx");
	}
	
	private static void getTextFromWord(String path) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		POIXMLTextExtractor extractor = null;
		File file = new File(path);
		try {
//			is = new FileInputStream(new File("C:/Users/xuyin/Desktop/1 - 副本.doc"));
//			WordExtractor ex = new WordExtractor(is);
//			String text2003 = ex.getText();
//			System.out.println(text2003);
			System.out.println("-------------------------------------------------");
			fis = new FileInputStream(file);
			XWPFDocument doc = new XWPFDocument(fis);
			extractor = new XWPFWordExtractor(doc);
			//获取文字内容
			String content = extractor.getText();
			System.out.println(content);
			//获取图片
			List<XWPFPictureData> picList = doc.getAllPictures();
			for (XWPFPictureData pic : picList) {
				 System.out.println(pic.getPictureType() + file.separator + pic.suggestFileExtension() +file.separator+pic.getFileName());
				 byte[] byteValue = pic.getData();
				 fos = new FileOutputStream("C:/Users/xuyin/Desktop/test_image/word/"+pic.getFileName()); 
			     fos.write(byteValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(fis!=null){
					fis.close();
				}
				if(fos!=null){
					fos.close();
				}
				if(extractor!=null){
					extractor.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		
	}

	public static String getTextFromPDF(String pdfFilePath) 
    {  
		PDDocument doc = null;
		OutputStream out = null;
		try {
			doc = PDDocument.load(new File(pdfFilePath));
//			PDFTextStripper stripper = new PDFTextStripper();
			PDFLayoutTextStripper stripper = new PDFLayoutTextStripper();
			String text = stripper.getText(doc);//文档内容
			System.out.println(text);
			System.out.println("------------------");
			int numberOfPages = doc.getNumberOfPages();//总页数
			for (int i = 0; i < numberOfPages; i++) {
				//获取第一页
				PDPage page = doc.getPage(i);
				PDResources resources = page.getResources();
				Iterable<COSName> iterable = resources.getXObjectNames();
				int j = 0;
				if(iterable!=null){
					Iterator<COSName> it = iterable.iterator();
					while (it.hasNext()) {
						COSName cosName = it.next();
						if(resources.isImageXObject(cosName)){
							PDImageXObject image = (PDImageXObject) resources.getXObject(cosName);
							File file = new File("C:/Users/xuyin/Desktop/test_image/pdf/"+j+".jpg");
							out = new FileOutputStream(file);
							BufferedImage image2 = image.getImage();
							ImageIO.write(image2, "jpg", out);
							j++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
        return "";  
    }  
	
	// 去读Excel的方法readExcel，该方法的入口参数为一个File对象  
    public static void readExcel(File file) throws Exception {  
//    	HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
//        HSSFSheet sheet = null;
//        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
//            sheet = workbook.getSheetAt(i);
//            for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {// getLastRowNum，获取最后一行的行标
//                HSSFRow row = sheet.getRow(j);
//                if (row != null) {
//                    for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
//                        if (row.getCell(k) != null) { // getCell 获取单元格数据
//                            System.out.print(row.getCell(k) + "\t");
//                        } else {
//                            System.out.print("\t");
//                        }
//                    }
//                }
//                System.out.println(""); // 读完一行后换行
//            }
//            System.out.println("读取sheet表：" + workbook.getSheetName(i) + " 完成");
//        }
    }  
}
