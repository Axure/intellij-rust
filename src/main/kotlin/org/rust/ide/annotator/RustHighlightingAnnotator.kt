package org.rust.ide.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import org.rust.ide.colors.RustColor
import org.rust.lang.core.psi.*
import org.rust.lang.core.psi.impl.mixin.isMut

// Highlighting logic here should be kept in sync with tags in RustColorSettingsPage
class RustHighlightingAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : RustElementVisitor() {
        override fun visitAttr(o: RustAttrElement) {
            holder.highlight(o, RustColor.ATTRIBUTE)
        }

        override fun visitMacroInvocation(m: RustMacroInvocationElement) {
            holder.highlight(m, RustColor.MACRO)
        }

        override fun visitTypeParam(o: RustTypeParamElement) {
            holder.highlight(o, RustColor.TYPE_PARAMETER)
        }

        override fun visitPatBinding(o: RustPatBindingElement) {
            if (o.isMut) {
                holder.highlight(o.identifier, RustColor.MUT_BINDING)
            }
        }

        override fun visitPath(o: RustPathElement) {
            o.reference.resolve().let {
                if (it is RustPatBindingElement && it.isMut) {
                    holder.highlight(o.identifier, RustColor.MUT_BINDING)
                }
            }
        }

        override fun visitFnItem(o: RustFnItemElement) {
            holder.highlight(o.identifier, RustColor.FUNCTION_DECLARATION)
        }

        override fun visitImplMethodMember(o: RustImplMethodMemberElement) {
            val color = if (o.isStatic) RustColor.STATIC_METHOD else RustColor.INSTANCE_METHOD
            holder.highlight(o.identifier, color)
        }

        override fun visitTraitMethodMember(o: RustTraitMethodMemberElement) {
            val color = if (o.isStatic) RustColor.STATIC_METHOD else RustColor.INSTANCE_METHOD
            holder.highlight(o.identifier, color)
        }
    })
}

private fun AnnotationHolder.highlight(element: PsiElement?, color: RustColor) {
    if (element != null) {
        createInfoAnnotation(element, null).textAttributes = color.textAttributesKey
    }
}
