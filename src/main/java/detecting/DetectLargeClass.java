package detecting;


import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * Class to provide detecting: 'LargeClass'
 *
 * @author Jinyoung Kim
 */
public class DetectLargeClass extends BaseDetectAction {

    public Project project;
    //private PsiMethod focusMethod;

    /* Returns the story ID. */
    @Override
    public String storyID() {
        return "DLC";
    }

    /* Returns the story name as a string format for message. */
    @Override
    public String storyName() {
        return "Detect Large Class";
    }

    /* Returns the description of each story. (in html-style) */
    @Override
    public String description() {
        return "<html>When there are too many methods or fields in the class<br/>" +
                " ,detect it as code smell large class.</html>";
    }

    /* Returns the precondition of each story. (in html-style) */
    @Override
    public String precondition() {
        return "<html>There are more methods or fields in the class than a set standard</html>";
    }

    /**
     * Method that checks whether candidate method has long parameter list
     *
     * @param e AnActionEvent
     * @return true if method has smell code
     */
    @Override
    public boolean detectSmell(AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return false;
        }

        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return false;
        }

        Document document = editor.getDocument();
        PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document);
        if (psiFile == null) {
            return false;
        }

        PsiElement elementAtCaret = psiFile.findElementAt(editor.getCaretModel().getOffset());
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAtCaret, PsiClass.class);
        if (psiClass == null) {
            return false;
        }

        return isLargeClass(psiClass);
    }

    /**
     * Helper method to check if the class is considered 'large'.
     *
     * @param psiClass PsiClass
     * @return true if the class is larger than set thresholds for fields and methods
     */
    private boolean isLargeClass(PsiClass psiClass) {
        final int MAX_FIELDS = 5;  // Example threshold for maximum allowed fields
        final int MAX_METHODS = 5; // Example threshold for maximum allowed methods

        PsiField[] fields = psiClass.getFields();
        PsiMethod[] methods = psiClass.getMethods();

        return fields.length > MAX_FIELDS || methods.length > MAX_METHODS;
    }

}
