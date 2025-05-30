package com.practice;

/**
 * @author yangkuo
 * @date 2023/10/11
 * @description
 */
import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * 说明：验证jar完整
 * 作者：FH Admin
 * from：fhadmin.cn
 */
public class FindInvalidJarAndDel extends DirectoryWalker<String> {
    public static void main(String[] args) throws IOException {
        // 查找本地maven仓库
        File startDir = new File("/Users/hanson/.m2/repository");
        FindInvalidJarAndDel finder = new FindInvalidJarAndDel();
        List<String> finded = new ArrayList<>();
        finder.walk(startDir, finded);
        if (finded.size() > 0) {
            //删除对应的文件
            for (String f : finded) {
                System.out.println("需要删除 " + f);
                try {
                    FileUtils.forceDelete(new File(f));//删除整个文件夹
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void handleFile(File file, int depth, Collection<String> results) throws IOException {
        if (results.contains(file.getParent())) {
            return;
        }
        if (file.getName().endsWith(".lastUpdated")||file.getName().toLowerCase().endsWith("resolver-status.properties")) {
            results.add(file.getParent());
            return;
        }
        if (file.getName().endsWith(".jar")) {
            //尝试解压一下，如果不能解压，则说明jar包有问题
            try {
                ZipFile zip = new ZipFile(file);
                Enumeration zipEntries = zip.entries();
                while (zipEntries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) zipEntries.nextElement();
                    entry.getName();
                    entry.getSize();
                }
            } catch (Exception e) {
                results.add(file.getParent());
                return;
            }
        }
    }
}