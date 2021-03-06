package org.rust.lang.core.stubs

import org.rust.lang.core.stubs.elements.*

fun factory(name: String): RustNamedStubElementType<*, *> = when (name) {
    "CONST_ITEM"          -> RustConstItemStubElementType
    "ENUM_ITEM"           -> RustEnumItemStubElementType
    "FN_ITEM"             -> RustFnItemStubElementType
    "IMPL_METHOD_MEMBER"  -> RustImplMethodMemberStubElementType
    "MOD_DECL_ITEM"       -> RustModDeclItemStubElementType
    "MOD_ITEM"            -> RustModItemStubElementType
    "STATIC_ITEM"         -> RustStaticItemStubElementType
    "STRUCT_ITEM"         -> RustStructItemStubElementType
    "TRAIT_ITEM"          -> RustTraitItemStubElementType
    "TRAIT_METHOD_MEMBER" -> RustTraitMethodMemberStubElementType
    "TYPE_ITEM"           -> RustTypeItemStubElementType

    else               -> {
        throw IllegalArgumentException("Unknown element $name")
    }
}
