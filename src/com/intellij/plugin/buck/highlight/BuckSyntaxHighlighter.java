package com.intellij.plugin.buck.highlight;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.plugin.buck.lang.BuckLexerAdapter;
import com.intellij.plugin.buck.lang.psi.BuckTypes;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

public class BuckSyntaxHighlighter extends SyntaxHighlighterBase {
  public static final TextAttributesKey SEPARATOR = createTextAttributesKey(
      "BUCK_SEPARATOR", DefaultLanguageHighlighterColors.OPERATION_SIGN);
  public static final TextAttributesKey KEY = createTextAttributesKey(
      "BUCK_KEY", DefaultLanguageHighlighterColors.KEYWORD);
  public static final TextAttributesKey VALUE = createTextAttributesKey(
      "BUCK_VALUE", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey COMMENT = createTextAttributesKey(
      "BUCK_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
  public static final TextAttributesKey RULE_NAME = createTextAttributesKey(
      "BUCK_RULE_NAME", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION);
  public static final TextAttributesKey BUCK_STRING = createTextAttributesKey(
      "BUCK_STRING", DefaultLanguageHighlighterColors.STRING);
  public static final TextAttributesKey BUCK_BRACES = createTextAttributesKey(
      "BUCK_BRACES", DefaultLanguageHighlighterColors.BRACES);
  public static final TextAttributesKey BUCK_COMMA = createTextAttributesKey(
      "BUCK_COMMA", DefaultLanguageHighlighterColors.COMMA);
  public static final TextAttributesKey BUCK_SEMICOLON = createTextAttributesKey(
      "BUCK_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
  public static final TextAttributesKey BUCK_EQUAL = createTextAttributesKey(
      "BUCK_EQUAL", DefaultLanguageHighlighterColors.OPERATION_SIGN);
  public static final TextAttributesKey BUCK_MACRO = createTextAttributesKey(
      "BUCK_MACRO", DefaultLanguageHighlighterColors.STATIC_FIELD);

  static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("BUCK_BAD_CHARACTER",
      new TextAttributes(Color.RED, null, null, null, Font.BOLD));

  private static final TextAttributesKey[] BAD_CHAR_KEYS = new TextAttributesKey[]{BAD_CHARACTER};
  private static final TextAttributesKey[] SEPARATOR_KEYS = new TextAttributesKey[]{SEPARATOR};
  private static final TextAttributesKey[] KEY_KEYS = new TextAttributesKey[]{KEY};
  private static final TextAttributesKey[] VALUE_KEYS = new TextAttributesKey[]{VALUE};
  private static final TextAttributesKey[] COMMENT_KEYS = new TextAttributesKey[]{COMMENT};
  private static final TextAttributesKey[] RULE_NAME_KEYS = new TextAttributesKey[]{RULE_NAME};
  private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{BUCK_STRING};
  private static final TextAttributesKey[] BRACES_KEYS = new TextAttributesKey[]{BUCK_BRACES};
  private static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[]{BUCK_COMMA};
  private static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[]{BUCK_SEMICOLON};
  private static final TextAttributesKey[] EQUAL_KEYS = new TextAttributesKey[]{BUCK_EQUAL};
  private static final TextAttributesKey[] MACROS_KEYS = new TextAttributesKey[]{BUCK_MACRO};
  private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

  @NotNull
  @Override
  public Lexer getHighlightingLexer() {
    return new BuckLexerAdapter();
  }

  @NotNull
  @Override
  public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
    if (tokenType.equals(BuckTypes.KEYWORDS)) {
      return KEY_KEYS;
    } else if (tokenType.equals(BuckTypes.VALUE)) {
      return VALUE_KEYS;
    } else if (tokenType.equals(BuckTypes.RULE_NAMES)) {
      return RULE_NAME_KEYS;
    } else if (tokenType.equals(BuckTypes.VALUE_STRING)) {
      return STRING_KEYS;
    } else if (tokenType.equals(BuckTypes.VALUE_BOOLEAN)) {
      return KEY_KEYS;
    } else if (tokenType.equals(BuckTypes.LBRACE)) {
      return BRACES_KEYS;
    } else if (tokenType.equals(BuckTypes.RBRACE)) {
      return BRACES_KEYS;
    } else if (tokenType.equals(BuckTypes.COMMA)) {
      return COMMA_KEYS;
    } else if (tokenType.equals(BuckTypes.SEMICOLON)) {
      return SEMICOLON_KEYS;
    } else if (tokenType.equals(BuckTypes.EQUAL)) {
      return EQUAL_KEYS;
    } else if (tokenType.equals(BuckTypes.COMMENT)) {
      return COMMENT_KEYS;
    } else if (tokenType.equals(BuckTypes.MACROS)) {
      return MACROS_KEYS;
    } else if (tokenType.equals(TokenType.BAD_CHARACTER)) {
      return BAD_CHAR_KEYS;
    } else {
      return EMPTY_KEYS;
    }
  }

}
