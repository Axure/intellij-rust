package org.rust.ide.formatter.settings

import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.openapi.application.ApplicationBundle
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider.SettingsType.*
import org.rust.ide.utils.loadCodeSampleResource
import org.rust.lang.RustLanguage

class RustLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage(): Language = RustLanguage

    override fun getCodeSample(settingsType: SettingsType): String =
        // TODO Provide more suitable code samples for specific settings types
        when (settingsType) {
            BLANK_LINES_SETTINGS,
            WRAPPING_AND_BRACES_SETTINGS,
            INDENT_SETTINGS -> CODE_SAMPLE

            else -> ""
        }

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {
        @Suppress("NON_EXHAUSTIVE_WHEN")
        when (settingsType) {
            BLANK_LINES_SETTINGS ->
                consumer.showStandardOptions(
                    "KEEP_LINE_BREAKS",
                    "KEEP_BLANK_LINES_IN_DECLARATIONS",
                    "KEEP_BLANK_LINES_IN_CODE")

            WRAPPING_AND_BRACES_SETTINGS -> {
                consumer.showStandardOptions(
                    "RIGHT_MARGIN",
                    "ALIGN_MULTILINE_CHAINED_METHODS",
                    "ALIGN_MULTILINE_PARAMETERS",
                    "ALIGN_MULTILINE_PARAMETERS_IN_CALLS")

                consumer.showCustomOption(RustCodeStyleSettings::class.java,
                    "ALIGN_RET_TYPE_AND_WHERE_CLAUSE",
                    "Align return type and where clause",
                    CodeStyleSettingsCustomizable.WRAPPING_METHOD_PARAMETERS)

                consumer.showCustomOption(RustCodeStyleSettings::class.java,
                    "ALIGN_WHERE_BOUNDS",
                    ApplicationBundle.message("wrapping.align.when.multiline"),
                    "Where clause bounds")
            }
        }
    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor? = SmartIndentOptionsEditor()

    override fun getDefaultCommonSettings(): CommonCodeStyleSettings =
        CommonCodeStyleSettings(language).apply {
            RIGHT_MARGIN = 100
            ALIGN_MULTILINE_PARAMETERS_IN_CALLS = true
            initIndentOptions().apply {
                // FIXME(jajakobyly): It's a hack
                // Nobody else does this and still somehow achieve similar effect
                CONTINUATION_INDENT_SIZE = INDENT_SIZE
            }
        }

    private val CODE_SAMPLE: String by lazy {
        loadCodeSampleResource("org/rust/ide/formatter/settings/code_sample.rs")
    }
}
