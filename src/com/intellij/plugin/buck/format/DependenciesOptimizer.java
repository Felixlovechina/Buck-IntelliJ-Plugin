package com.intellij.plugin.buck.format;

import com.intellij.plugin.buck.lang.psi.BuckArrayElements;
import com.intellij.plugin.buck.lang.psi.BuckProperty;
import com.intellij.plugin.buck.lang.psi.BuckPropertyLvalue;
import com.intellij.plugin.buck.lang.psi.BuckValue;
import com.intellij.plugin.buck.lang.psi.BuckValueArray;
import com.intellij.plugin.buck.lang.psi.BuckVisitor;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * A utility class for sorting buck dependencies alphabetically
 */
public class DependenciesOptimizer {

  private static String DEPENDENCIES_KEYWORD = "deps";

  public static void optimzeDeps(@NotNull PsiFile file) {
    final PropertyVisitor visitor = new PropertyVisitor();
    file.accept(new BuckVisitor() {
      @Override
      public void visitElement(PsiElement node) {
        node.acceptChildren(this);
        node.accept(visitor);
      }
    });

    // Commit modifications
    final PsiDocumentManager manager = PsiDocumentManager.getInstance(file.getProject());
    manager.doPostponedOperationsAndUnblockDocument(manager.getDocument(file));
  }

  private static class PropertyVisitor extends BuckVisitor {
    @Override
    public void visitProperty(@NotNull BuckProperty property) {
      BuckPropertyLvalue lValue = property.getPropertyLvalue();
      if (lValue == null || !lValue.getText().equals(DEPENDENCIES_KEYWORD)) {
        return;
      }

      List<BuckValue> values = property.getExpression().getValueList();
      for (BuckValue value : values) {
        BuckValueArray array = value.getValueArray();
        if (array != null) {
          sortArray(array);
        }
      }
    }
  }

  private static void sortArray(BuckValueArray array) {
    BuckArrayElements arrayElements = array.getArrayElements();
    PsiElement[] arrayValues = arrayElements.getChildren();
    Arrays.sort(arrayValues, new Comparator<PsiElement>() {
          @Override
          public int compare(PsiElement e1, PsiElement e2) {
            return compareDependencyStrings(e1.getText(), e2.getText());
          }
        }
    );
    PsiElement[] oldValues = new PsiElement[arrayValues.length];
    for (int i = 0; i < arrayValues.length; ++i) {
      oldValues[i] = arrayValues[i].copy();
    }

    for (int i = 0; i < arrayValues.length; ++i) {
      arrayElements.getChildren()[i].replace(oldValues[i]);
    }
  }

  /**
   * Use our own method to compare 'deps' stings.
   * 'deps' should be sorted with local references ':' preceding any cross-repo references '@'
   * e.g :inner, //world:empty, //world/asia:jp, //world/europe:uk, @mars, @moon
   */
  private static int compareDependencyStrings(String baseString, String anotherString) {
    for (int i = 0; i < Math.min(baseString.length(), anotherString.length()); ++i) {
      char c1 = baseString.charAt(i);
      char c2 = anotherString.charAt(i);
      if (c1 == c2) {
        continue;
      } else if (c1 == ':') {
        return -1;
      } else if (c2 == ':') {
        return 1;
      } else if (c1 == '@') {
        return 1;
      } else if (c2 == '@') {
        return -1;
      } else if (c1 < c2) {
        return -1;
      } else {
        return 1;
      }
    }
    return baseString.compareTo(anotherString);
  }

}
