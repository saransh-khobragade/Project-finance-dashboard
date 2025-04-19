package com.example.finance.utility

import com.example.finance.model.Transaction
import com.example.finance.model.TransactionType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class FileUtils{

    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    /**
     * Reads a CSV file, parses its contents, and maps the data to a list of Transaction objects.
     * @return List of Transaction objects.
     */
    fun getTransactions(file: MultipartFile): List<Transaction> {
        return parseTransactions(saveMultipartFile(file))
    }

    private fun isNumeric(value: String): Boolean {
        return value.toDoubleOrNull() != null
    }

    fun saveMultipartFile(file: MultipartFile): File {
        val tempFile = File.createTempFile("uploaded_", ".csv")
        file.inputStream.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return tempFile
    }

    fun parseTransactions(file: File): List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        file.useLines { lines ->
            lines.drop(1).forEach { line -> // Skip the header if present
                val columns = line.split(",").map { it.trim() }
                if (columns.size >= 7) {
                    val amount = when {
                        isNumeric(columns[3]) && columns[3].toDouble() > 0 -> columns[3].toDouble()
                        isNumeric(columns[4]) -> columns[4].toDouble()
                        else -> 0.0 // Default value if neither column is valid
                    }
                    val transaction = Transaction(
                        transactionId = columns[5],
                        summary = columns[1],
                        transactionType = if (columns[3].toDouble() > 0) TransactionType.DEBIT else TransactionType.CREDIT,
                        transactionDate = LocalDate.parse(columns[0], dateFormatter),
                        amount = amount
                    )
                    transactions.add(transaction)
                }
            }
        }
        return transactions
    }



}
