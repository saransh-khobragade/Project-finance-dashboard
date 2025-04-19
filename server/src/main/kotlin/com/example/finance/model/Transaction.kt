package com.example.finance.model

import java.time.LocalDate

data class Transaction(
    val transactionId: String,
    val summary: String,
    val transactionType: TransactionType,
    val transactionDate: LocalDate,
    val amount: Double
)

enum class TransactionType {
    CREDIT,
    DEBIT
}
