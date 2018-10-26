
package sample;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Configs {
    public static final String Username = "";
    public static final String[] KEYWORDS = new String[]{"C","A","B","D","F","G","-","5","1","2","3","4","6","7","8","9","--","---","0"};
    public static final String KEYWORD_PATTERN;
    public static final String PAREN_PATTERN = "\\(|\\)";
    public static final String BRACE_PATTERN = "\\{|\\}";
    public static final String BRACKET_PATTERN = "\\[|\\]";
    public static final String SEMICOLON_PATTERN = "\\;";
    public static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    public static final String COMMENT_PATTERN = "//[^\n]*|/\\*(.|\\R)*?\\*/";
    public static final Pattern PATTERN;
    public static final String sampleCode;
    public static String[] EXPRESIONES;

    public Configs() {
    }

    public static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;

        StyleSpansBuilder spansBuilder;
        for(spansBuilder = new StyleSpansBuilder(); matcher.find(); lastKwEnd = matcher.end()) {
            String styleClass = matcher.group("KEYWORD") != null ? "keyword" : (matcher.group("PAREN") != null ? "paren" : (matcher.group("BRACE") != null ? "brace" : (matcher.group("BRACKET") != null ? "bracket" : (matcher.group("SEMICOLON") != null ? "semicolon" : (matcher.group("STRING") != null ? "string" : (matcher.group("COMMENT") != null ? "comment" : null))))));

            assert styleClass != null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
        }

        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    static {
        KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
        PATTERN = Pattern.compile("(?<KEYWORD>" + KEYWORD_PATTERN + ")|(?<PAREN>" + "\\(|\\)" + ")|(?<BRACE>" + "\\{|\\}" + ")|(?<BRACKET>" + "\\[|\\]" + ")|(?<SEMICOLON>" + "\\;" + ")|(?<STRING>" + "\"([^\"\\\\]|\\\\.)*\"" + ")|(?<COMMENT>" + "//[^\n]*|/\\*(.|\\R)*?\\*/" + ")");
        sampleCode = String.join("\n",
                "--- -- -- ---",
                 "C-5 01 44 F50",
                 "--- -- -- F20",
                 "C-5 01 32 F50",
                 "--- -- -- F60",
                 "C#5 01 12 F60",
                "--- -- -- F60",
                "C-5 01 45 F60",
                "--- -- -- F90",
                "D#5 01 52 F60",
                "G-5 01 -- F90",
                "--- -- -- F60",
                "A#5 01 45 F90",
                "G-5 01 -- F60",
                "--- -- -- F90"
//C = nota,
//-# = tipo de nota,
//1-9 = tonalidad,
//00-99 = hihat,
//00-99 = Volumen de la nota,
//F00-F99 = TEMPO
                );
        EXPRESIONES = new String[]{"[a-gA-G_-]{1}[-#]{1}[0-9_-]{1} [0-9_-]{1}[0-9_-]{1} [0-9_-]{1}[0-9_-]{1} [fF_-][0-9_-][0-9_-]"};
    }
}
