package xyz.hanabinoir.photoviewerxyzen

import org.junit.Test
import xyz.hanabinoir.photoviewerxyzen.util.Common

class UtilTest {
    private val util = Common()
    @Test
    fun keyword_check() {
        val checkList = listOf(
            // Normal Cases 正常例
            Pair("asdf", "asdf"), // Single World 単語
            Pair("Lorem Ipsum", "Lorem Ipsum"), // Phrase with single white space 半角空白
            Pair("", null), // Empty 空
            // Irregular Cases 非常例
            Pair("   asdf ", "asdf"), // Surrounded by white spaces 両側に空白あり
            Pair("  Lorem   Ipsum  ", "Lorem Ipsum"), // Multiple white spaces in the middle 真ん中に空白あり
//            Pair("Lorem　Ipsum", "Lorem Ipsum") // Full-width space 全角空白
        )
        checkList.forEach { pair ->
            val result = util.optimizeKeywords(pair.first)
            assert(result == pair.second)
        }
    }
}