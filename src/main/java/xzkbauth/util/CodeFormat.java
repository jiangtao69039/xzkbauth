package xzkbauth.util;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CodeFormat {

    //务必在commit后再格式化,非utf-8编码文件可能被格式化出错
    public static void main(String[] args) throws Exception {
        codeFormatThisProject();
    }

    /**
     * 获取本项目所有 .java文件的绝对路径
     * @return
     */
    public static List<String> getAbsoluteJavaFilePath() throws Exception{
        String path = System.getProperty("user.dir");
        path = path + File.separator+"src";
        Path root = Paths.get(path);
        List<String> absolutePaths = new ArrayList<>();
        List<String> finalAbsolutePaths = absolutePaths;
        Files.walkFileTree(
                root,
                new SimpleFileVisitor<Path>() {
                    // 访问文件时触发
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        String f = file.toFile().getAbsolutePath();
                        if (f.endsWith(".java")) {
                            finalAbsolutePaths.add(f);
                            return FileVisitResult.CONTINUE;
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
        absolutePaths=finalAbsolutePaths;

        if (absolutePaths.isEmpty()){
            return new ArrayList<>();
        }
        //去掉CodeFormat.java
        absolutePaths=absolutePaths
                .stream()
                .filter(str-> (!str.endsWith("CodeFormat.java")))
                //.filter(str->(!str.endsWith("Application.java")))
                .collect(Collectors.toList());
        return absolutePaths;
    }

    /**
     * 格式化一个java文件
     * @param javaFilePathString
     * @return
     * @throws Exception
     */
    public static String formatOneFile(String javaFilePathString) throws FormatterException, IOException {
        File f = new File(javaFilePathString);
        Path javaFilePath = f.toPath();

        byte[] bytes = Files.readAllBytes(javaFilePath);
        String sourceString = new String(bytes, StandardCharsets.UTF_8);
        String formattedSource = new Formatter().formatSource(sourceString);
        if(formattedSource.equals(sourceString)){
            //System.out.println(javaFilePathString+" 格式化后无变动,不写入");
            return null;
        }
        return formattedSource;
    }

    /**
     * 写入字符串formattedSource到文件javaFileString中
     * @param javaFileString 待写入文件
     * @param formattedSource  待写入字符串
     * @throws Exception
     */
    public static void writeFormatResultToFile(String javaFileString,String formattedSource) throws Exception{
        Writer writer =new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(new File(javaFileString),false), StandardCharsets.UTF_8));
        writer.write(formattedSource);
        writer.flush();
        writer.close();
        System.out.println(javaFileString + " 格式化完成");
    }

    public static void codeFormatThisProject() throws Exception{
        List<String> absolutePaths=getAbsoluteJavaFilePath();

        for (String javaFileString : absolutePaths) {
            /*String code = getCode(javaFileString);
            if(!"UTF-8".equals(code)){
                System.out.println(javaFileString+" 的编码是:"+code+" ,只支持utf-8格式化");
                continue;
            }*/
           String formattedSourceResult = formatOneFile(javaFileString);
            //null表示格式化文件后内容无变动
            if(formattedSourceResult==null){
                continue;
            }
            String formattedSource = formattedSourceResult;
            writeFormatResultToFile(javaFileString,formattedSource);
        }
    }

    /*public static String getCode(String file){
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        //ASCIIDetector用于ASCII编码测定
        detector.add(ASCIIDetector.getInstance());
        //UnicodeDetector用于Unicode家族编码的测定
        detector.add(UnicodeDetector.getInstance());
        java.nio.charset.Charset charset = null;
        File f=new File(file);
        try {
            charset = detector.detectCodepage(f.toURL());
        } catch (Exception ex) {ex.printStackTrace();}
        if(charset!=null){
            //GB2312 UTF-8
            //System.out.println(f.getName()+"编码是："+charset.name());
            return charset.name();
        }else{
            //System.out.println(f.getName()+"未知");
            return "";
        }

    }*/
}
