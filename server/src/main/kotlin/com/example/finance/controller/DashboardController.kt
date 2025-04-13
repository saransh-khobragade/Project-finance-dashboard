package com.example.finance.controller

import org.openapitools.api.FinanceApi
import org.openapitools.model.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class DashboardController : FinanceApi {

    override fun getFinanceDashboard(): ResponseEntity<Dashboard> {
        // Replace with actual implementation to fetch finance dashboard
        val exampleDashboard = Dashboard().apply {
            barChart = DashboardBarChart().apply {
                columns = setOf(BarChartCategory.DEBIT, BarChartCategory.CREDIT)
                values = listOf(150, 500)
            }
            pieChart = listOf(
                DashboardPieChart().apply {
                    id = "Category1"
                    value = 60
                    label = PieChartCategory.FOOD
                },
                DashboardPieChart().apply {
                    id = "Category2"
                    value = 40
                    label = PieChartCategory.MICS
                },
                DashboardPieChart().apply {
                    id = "Category3"
                    value = 400
                    label = PieChartCategory.UTILITY
                }
            )
        }
        return ResponseEntity.ok(exampleDashboard)
    }
}