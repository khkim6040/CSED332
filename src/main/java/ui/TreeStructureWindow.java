package ui;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.markup.HighlighterLayer;
import com.intellij.openapi.editor.markup.HighlighterTargetArea;
import com.intellij.openapi.editor.markup.MarkupModel;
import com.intellij.openapi.editor.markup.RangeHighlighter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiStatement;
import com.intellij.ui.ColoredTreeCellRenderer;
import com.intellij.ui.JBColor;
import com.intellij.ui.treeStructure.Tree;
import detecting.BaseDetectAction;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.jetbrains.annotations.NotNull;


public class TreeStructureWindow extends Tree {

    private static final Icon Icon1 = IconLoader.getIcon("/general/projectStructure.svg",
        TreeStructureWindow.class.getClassLoader());
    private static final Icon Icon2 = IconLoader.getIcon("/nodes/configFolder.svg",
        TreeStructureWindow.class.getClassLoader());
    private static final Icon Icon3 = IconLoader.getIcon("/nodes/editorconfig.svg",
        TreeStructureWindow.class.getClassLoader());
    private RangeHighlighter currentHighlighter;

    /**
     * Creates a project structure tree for a given project.
     *
     * @param project a project
     */
    TreeStructureWindow(@NotNull Project project, Map<String, List<PsiElement>> result) {
        setModel(ProjectTreeModelFactory.createProjectTreeModel(project, result));

        // Set a cell renderer to display the name and icon of each node
        setCellRenderer(new ColoredTreeCellRenderer() {
            @Override
            public void customizeCellRenderer(@NotNull JTree tree, Object value, boolean selected,
                boolean expanded, boolean leaf, int row, boolean hasFocus) {
                if (value instanceof DefaultMutableTreeNode) {
                    Object v = ((DefaultMutableTreeNode) value).getUserObject();
                    int lineNumber = 0;
                    String fileName = "";
                    if (v instanceof PsiElement) {
                        PsiElement element = (PsiElement) v;
                        PsiFile file = element.getContainingFile();
                        if (file != null) {
                            Document document = FileDocumentManager.getInstance().getDocument(file.getVirtualFile());
                            if (document != null) {
                                int textOffset = element.getTextOffset();
                                fileName = file.getName();
                                lineNumber = document.getLineNumber(textOffset) + 1;
                            }
                        }
                    }
                    if (v instanceof Project) { // Project
                        setIcon(Icon1);
                        append(((Project) v).getName());
                    } else if (v instanceof BaseDetectAction) {
                        setIcon(Icon2);
                        append(((BaseDetectAction) v).storyName());
                    } else if (v instanceof PsiField) {
                        setIcon(Icon3);
                        append("[" + fileName + "] " + ((PsiField) v).getName() + ", line " + lineNumber);
                    } else if (v instanceof PsiComment) {
                        setIcon(Icon3);
                        append("[" + fileName + "] Comment, line " + lineNumber);
                    } else if (v instanceof PsiClass) {
                        setIcon(Icon3);

                        if (((PsiClass) v).getName() == null) { // Anonymous Class
                            append("Anonymous class");
                        } else {
                            append("[" + fileName + "] " + ((PsiClass) v).getName() + ", line " + lineNumber);
                        }
                    } else if (v instanceof PsiMethod) {
                        setIcon(Icon3);
                        append("[" + fileName + "] " + ((PsiMethod) v).getName() + ", line " + lineNumber);
                    } else if (v instanceof PsiParameter) {
                        setIcon(Icon3);
                        append("[" + fileName + "] " + ((PsiParameter) v).getName() + ", line " + lineNumber);
                    } else if (v instanceof PsiLocalVariable) {
                        setIcon(Icon3);
                        append("[" + fileName + "] " + ((PsiLocalVariable) v).getName() + ", line " + lineNumber);
                    } else if (v instanceof PsiCodeBlock) {
                        setIcon(Icon3);
                        append("[" + fileName + "] CodeBlock, line " + lineNumber);
                    } else if (v instanceof PsiStatement) {
                        setIcon(Icon3);
                        append("[" + fileName + "] Statement, line " + lineNumber);
                    }
                }

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    TreePath treePath = getClosestPathForLocation(e.getX(), e.getY());
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                    Object element = node.getUserObject();
                    Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
                    MarkupModel markupModel = editor.getMarkupModel();
                    if (currentHighlighter != null) {
                        markupModel.removeHighlighter(currentHighlighter);
                        currentHighlighter = null;
                    }

                    if (element != null) {
                        if (element instanceof PsiElement) {
                            int offset = ((PsiElement) element).getTextOffset();

                            TextAttributes attributes = new TextAttributes();
                            attributes.setBackgroundColor(JBColor.YELLOW);

                            currentHighlighter = markupModel.addRangeHighlighter(
                                offset,
                                offset,
                                HighlighterLayer.ERROR,
                                attributes,
                                HighlighterTargetArea.EXACT_RANGE
                            );
                            currentHighlighter.setErrorStripeMarkColor(JBColor.RED);
                            currentHighlighter.setErrorStripeTooltip("Codesmell Detected");
                        }
                    }
                }

                if (e.getClickCount() == 2) {
                    TreePath treePath = getClosestPathForLocation(e.getX(), e.getY());
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                    Object element = node.getUserObject();

                    if (element != null) {
                        if (element instanceof PsiElement) {
                            VirtualFile f = ((PsiElement) element).getContainingFile().getVirtualFile();
                            int offset = ((PsiElement) element).getTextOffset();
                            OpenFileDescriptor fd = new OpenFileDescriptor(project, f, offset);
                            fd.navigate(true);
                        }
                    }
                }

                if (SwingUtilities.isRightMouseButton(e)) {
                    TreePath treePath = getClosestPathForLocation(e.getX(), e.getY());
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
                    Object element = node.getUserObject();

                    CodescentPopUp menu = new CodescentPopUp(element);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
}