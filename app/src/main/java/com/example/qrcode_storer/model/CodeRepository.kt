package com.example.qrcode_storer.model

class CodeRepository(private val codeDao: CodeDao) {
    fun getAll() = codeDao.getAll()

    suspend fun insertCode(code: Code) = codeDao.insertCode(code)

    suspend fun delete(code: Code) = codeDao.delete(code)

    suspend fun deleteAll(codes: List<Code>) = codeDao.deleteAll(codes)

}