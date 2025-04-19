package com.example.finance.service

import com.example.finance.model.TransactionType
import com.example.finance.utility.FileUtils
import org.openapitools.model.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.regex.Pattern

@Service
class DashboardService(private val fileUtils: FileUtils) {

    fun getDashboard(file: MultipartFile): Dashboard {
        val transactions = fileUtils.getTransactions(file)

        var totalDebit = 0.0
        var totalCredit = 0.0
        val pieChartData = mutableMapOf<PieChartCategory, Double>()

        // Regex patterns for pie chart categories
        val regexMap = mapOf(
            Pattern.compile(".*(loan|REAL ESTATE).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.LOAN,
            Pattern.compile(".*(AUTOMOTIVE|MANSOOR AHMED|TOLL).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.CAR,
            Pattern.compile(".*(PARLOUR|PARLOR).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.PARLOR,
            Pattern.compile(".*(AMAZON|MYGLAMM|FLIPKART).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.SHOPPING,
            Pattern.compile(".*(MOVIE|ENTERTAINMENT|CINEPOLIS|NETFLIX|MULTIPLEX|CINEMA).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.ENTERTAINMENT,
            Pattern.compile(".*(BMTC|RAPIDO|OLA|QUICKRIDE).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.COMMUTE,
            Pattern.compile(".*(Jio|AIRTEL|RECHARGE).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.MOBILE,
            Pattern.compile(".*(HOLIDAY|IRCTC|TRIP|IBIBO|STAY|GOINDIGO|TOUR|DARJEELING CAB).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.TRAVEL,
            Pattern.compile(".*(GROCERIES|ZEPTO|HYPERMARKET|HYPER MARKET|VALUE MART|VEGE).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.GROCERY,
            Pattern.compile(".*SARANSH.*", Pattern.CASE_INSENSITIVE) to PieChartCategory.SARANSH,
            Pattern.compile(".*(CLOUDNINE|MOTHERHOOD|PHARMACY|MEDICAL|HEALTH|SAHYADRI|CHEMIST).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.MEDICAL,
            Pattern.compile(".*(ZOMATO|FOOD|SUBWAY|RESTAURANT|COFFEE|TEA|SNACKS|MCDONALDS).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.FOOD,
            Pattern.compile(".*(RENT|PEEYUSH|SANJOLI|BAKHRU|OZONE EVERGREEN).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.RENT,
            Pattern.compile(".*(BILL|COOK|CREDIT|ELECTRICIT|NOBROKER|MAID|MEETU|UPI-AXIS BANK LIMITED|MILKBASKET|ADDA).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.UTILITY,
            Pattern.compile(".*(ACH|INSTALLMENT|ZERODHA|BONDS|PAYUHDFCSTANDARDLIFE).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.INVESTMENT,
            Pattern.compile(".*UPI.*", Pattern.CASE_INSENSITIVE) to PieChartCategory.UPI,
            Pattern.compile(".*(SALARY|SAL|ORACLE|JPMC|MORGAN).*", Pattern.CASE_INSENSITIVE) to PieChartCategory.SALARY
        )

        transactions.forEach { transaction ->
            // Calculate bar chart totals
            when (transaction.transactionType) {
                TransactionType.DEBIT -> totalDebit += transaction.amount
                TransactionType.CREDIT -> totalCredit += transaction.amount
            }
            var isCategoryMatched = false
            // Match summary with regex and update pie chart data
            loop@ for ((pattern, category) in regexMap) {
                if (pattern.matcher(transaction.summary).matches()) {
                    pieChartData[category] = pieChartData.getOrDefault(category, 0.0) + transaction.amount
                    isCategoryMatched = true
                    break@loop // Exit the loop entirely once a match is found
                }
            }
            if (!isCategoryMatched) {
                // If no regex matched, categorize as MICS
                pieChartData[PieChartCategory.MICS] = pieChartData.getOrDefault(PieChartCategory.MICS, 0.0) + transaction.amount
            }
        }

        return Dashboard().apply {
            barChart = DashboardBarChart().apply {
                columns = setOf(BarChartCategory.DEBIT, BarChartCategory.CREDIT)
                values = listOf(totalDebit.toInt(), totalCredit.toInt())
            }
            pieChart = pieChartData.map { (category, amount) ->
                DashboardPieChart().apply {
                    id = category.name
                    value = amount.toInt()
                    label = category
                }
            }
        }
    }


}