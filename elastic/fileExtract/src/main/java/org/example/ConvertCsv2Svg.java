package org.example;


import com.aspose.cells.*;

public class ConvertCsv2Svg {
    public static void convertCsv2Svg(String filepath) throws Exception {
        // 렌더링할 CSV 파일 로드
        Workbook workbook = new Workbook(filepath);
// 컬렉션에서 기본 워크시트에 액세스
        Worksheet worksheet = workbook.getWorksheets().get(0);
// 결과 이미지에 대한 매개변수 정의
        ImageOrPrintOptions options = new ImageOrPrintOptions();
        options.setOnePagePerSheet(true);
        options.setImageType(ImageType.SVG);
// 워크시트를 SVG 형식의 이미지로 변환
        SheetRender renderer = new SheetRender(worksheet, options);
        renderer.toImage(0, filepath+".svg");
    }
}
