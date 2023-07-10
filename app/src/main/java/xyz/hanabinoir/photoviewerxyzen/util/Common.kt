package xyz.hanabinoir.photoviewerxyzen.util

class Common {
    fun optimizeKeywords(raw: String): String? {
        if (raw.isEmpty()) { return null }

        val output = raw
            .trim()
            .replace(Regex("\\s+"), " ")

        if (raw.isEmpty()) { return null }

        return output
    }
}
