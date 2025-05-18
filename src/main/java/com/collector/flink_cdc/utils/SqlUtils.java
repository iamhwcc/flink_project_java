package com.collector.flink_cdc.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author stalwarthuang
 * @description
 * @since 2025-05-14 星期三 23:59:53
 */
public class SqlUtils {
    public static List<String> extractDDLSqls(String path) {
        List<String> sqls = new ArrayList<>();

        File folder = new File(path);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".sql"));

            if (files != null) {
                for (File file : files) {
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()));
                        sqls.add(content);
                    } catch (IOException e) {
                        System.err.println("读取文件失败: " + file.getName());
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.err.println("路径不是有效的文件夹: " + path);
        }

        return sqls;
    }

    public static List<String> extractETLSqls(String path) {
        List<String> sqls = new ArrayList<>();

        File folder = new File(path);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".sql"));

            if (files != null) {
                Arrays.stream(files).sorted(Comparator.comparingInt(file -> {
                    String fileName = file.getName();
                    return Integer.parseInt(fileName.split("_")[0]);
                })).forEach(file -> {
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()));
                        sqls.add(content);
                    } catch (IOException e) {
                        System.err.println("读取文件失败: " + file.getName());
                        e.printStackTrace();
                    }
                });
            }
        } else {
            System.err.println("路径不是有效的文件夹: " + path);
        }
        return sqls;
    }
}
