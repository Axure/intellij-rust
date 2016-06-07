package org.rust.lang.core.psi.impl.mixin

import com.intellij.lang.ASTNode
import org.rust.lang.core.psi.RustUseItemElement
import org.rust.lang.core.psi.impl.RustCompositeElementImpl

abstract class RustUseItemImplMixin(node: ASTNode) : RustCompositeElementImpl(node), RustUseItemElement

val RustUseItemElement.isStarImport: Boolean get() = mul != null
