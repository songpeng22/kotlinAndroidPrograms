package com.example.encoder

import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

/*
 * MD5
 * */
object KnowledgeFactoryMD5 {
    @Throws(NoSuchAlgorithmException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val password = "knowledgefactory.net"
        val md = MessageDigest.getInstance("MD5")
        val hashInBytes = md.digest(password.
        toByteArray(StandardCharsets.UTF_8))
        val sb = StringBuilder()
        for (b in hashInBytes) {
            sb.append(String.format("%02x", b))
        }
        println("MD5:"+sb.toString())
    }
}

/*
 * SHA1
 * */
object KnowledgeFactorySHA1 {
    fun encryptThisString(input: String): String {
        return try {
            val md = MessageDigest.getInstance("SHA-1")
            val messageDigest = md.digest(input.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println("HashCode Generated by SHA-1 for: ")
        val s1 = "1"
        println("$s1 : ${encryptThisString(s1)}"
        )
    }
}

/*
 * SHA256
 * */
object KnowledgeFactorySHA256 {
    fun encryptThisString(input: String): String {
        return try {
            val md = MessageDigest.getInstance("SHA-256")
            val messageDigest = md.digest(input.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println("HashCode Generated by SHA-256 for: ")
        val s1 = "knowledgefactory.net"
        println("$s1 : ${encryptThisString(s1)}"
        )
    }
}

/*
 * SHA384
 * */
object KnowledgeFactorySHA384 {
    fun encryptThisString(input: String): String {
        return try {
            val md = MessageDigest.getInstance("SHA-384")
            val messageDigest = md.digest(input.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println("HashCode Generated by SHA-384 for: ")
        val s1 = "knowledgefactory.net"
        println("$s1 : ${encryptThisString(s1)}")
    }
}

/*
 * SHA512
 * */
object KnowledgeFactorySHA512 {
    fun encryptString(input: String): String {
        return try {
            val md = MessageDigest.getInstance("SHA-512")
            val messageDigest = md.digest(input.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println("HashCode Generated by SHA-512 for: ")
        val s1 = "knowledgefactory.net"
        println("$s1 : ${encryptString(s1)}")
    }
}

/*
 * PBKDF2
 * */
object KnowledgeFactoryPBKDF2 {
    @Throws(NoSuchAlgorithmException::class,
        InvalidKeySpecException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val originalPassword = "knowledgefactory.net"
        val generatedSecuredPasswordHash =
            generateStorngPasswordHash(originalPassword)
        println(generatedSecuredPasswordHash)
    }

    @Throws(NoSuchAlgorithmException::class,
        InvalidKeySpecException::class)
    private fun generateStorngPasswordHash
                (password: String): String {
        val iterations = 500
        val chars = password.toCharArray()
        val salt = salt
        val spec = PBEKeySpec(chars, salt, iterations, 64 * 8)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val hash = skf.generateSecret(spec).encoded
        return """
            Total iteration: $iterations
            Salt: ${toHex(salt)}
            Hash: ${toHex(hash)}
            """.trimIndent()
    }

    @get:Throws(NoSuchAlgorithmException::class)
    private val salt: ByteArray
        private get() {
            val sr: SecureRandom = SecureRandom.
            getInstance("SHA1PRNG")
            val salt = ByteArray(16)
            sr.nextBytes(salt)
            return salt
        }

    @Throws(NoSuchAlgorithmException::class)
    private fun toHex(array: ByteArray): String {
        val bi = BigInteger(1, array)
        val hex = bi.toString(16)
        val paddingLength = array.size * 2 - hex.length
        return if (paddingLength > 0) {
            String.format("%0" + paddingLength + "d", 0) + hex
        } else {
            hex
        }
    }
}