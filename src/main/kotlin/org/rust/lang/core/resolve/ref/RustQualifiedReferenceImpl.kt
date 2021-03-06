package org.rust.lang.core.resolve.ref

import com.intellij.psi.PsiElement
import org.rust.lang.core.completion.RustCompletionEngine
import org.rust.lang.core.psi.RustQualifiedReferenceElement
import org.rust.lang.core.psi.RustTokenElementTypes
import org.rust.lang.core.psi.referenceName
import org.rust.lang.core.resolve.RustResolveEngine


class RustQualifiedReferenceImpl(element: RustQualifiedReferenceElement)
    : RustReferenceBase<RustQualifiedReferenceElement>(element, element.referenceNameElement)
    , RustReference {

    override fun resolveVerbose(): RustResolveEngine.ResolveResult =
        RustResolveEngine.resolve(element)

    override fun getVariants(): Array<out Any> =
        RustCompletionEngine.complete(element)

    override fun isReferenceTo(element: PsiElement): Boolean {
        val target = resolve()
        return element.manager.areElementsEquivalent(target, element)
    }

    override fun toString(): String =
        element.let { qualRef ->
            var qual = qualRef.qualifier?.reference?.canonicalText
                              .orEmpty()

            if (qual.isNotEmpty())
                qual += RustTokenElementTypes.COLONCOLON.toString();

            qual + qualRef.referenceName
        }
}
