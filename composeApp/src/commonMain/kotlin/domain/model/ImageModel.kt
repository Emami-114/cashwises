package domain.model

data class ImageModel(
    val name: String,
    val path: String,
    val byteArray: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ImageModel

        if (name != other.name) return false
        if (path != other.path) return false
        if (!byteArray.contentEquals(other.byteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + path.hashCode()
        result = 31 * result + byteArray.contentHashCode()
        return result
    }
}