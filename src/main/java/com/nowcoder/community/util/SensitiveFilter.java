package com.nowcoder.community.util;


import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);

    private static final String REPLACEMENT = "***";

    //根结点
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                ){
            String keyword;
            while((keyword = reader.readLine())!= null) {
                this.addKeyWord(keyword);

            }

        } catch (IOException e) {
            logger.error("加载敏感词失败：" + e.getMessage());
        }

    }

    private void addKeyWord(String keyWord) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyWord.length(); i++) {
            char c = keyWord.charAt(i);
            TrieNode subNode = tempNode.getSubNodes(c);
            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            tempNode = subNode;
            if (i == keyWord.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text 带过滤的文本
     * @return 过滤后的文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }

        //指针1
        TrieNode tempNode = rootNode;
        //指针2
        int begin = 0;
        //指针3
        int position = 0;

        //结果
        StringBuilder sb = new StringBuilder();

        while (begin < text.length()) {
            if (position < text.length()) {
                char c = text.charAt(position);

                if (isSymbol(c)) {
                    if (tempNode == rootNode) {
                        sb.append(c);
                        begin++;
                    }
                    //无论符号在开头还是在中间，指针3都向下走一步
                    position++;
                    continue;
                }
                //检查下级节点
                tempNode = tempNode.getSubNodes(c);
                if (tempNode == null) {
                    sb.append(c);
                    begin++;
                    position++;
                    tempNode = rootNode;

                } else if (tempNode.isKeywordEnd()) {
                    sb.append(REPLACEMENT);
                    position++;
                    begin = position;

                    tempNode = rootNode;

                } else {
                    //检查下一个字符
                    position++;

                }


            } else {
                sb.append(text.charAt(begin));
                begin++;
                position = begin;
                tempNode = rootNode;
            }

        }


        return sb.toString();
    }

    private boolean isSymbol(Character c) {
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    //前缀树
    private class TrieNode {
        //关键词结束标志
        private boolean isKeywordEnd = false;

        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        public void addSubNode(Character c, TrieNode node) {
            subNodes.put(c, node);

        }

        // 根据值取对应的子节点
        public TrieNode getSubNodes(Character c) {
            return subNodes.get(c);
        }
    }
}
