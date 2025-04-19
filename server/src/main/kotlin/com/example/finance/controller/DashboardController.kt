package com.example.finance.controller

import com.example.finance.service.DashboardService
import org.openapitools.api.FinanceApi
import org.openapitools.model.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class DashboardController(private val dashboardService: DashboardService) : FinanceApi {

    override fun getFinanceDashboard(file: MultipartFile): ResponseEntity<Dashboard> {
        val dashboard = dashboardService.getDashboard(file)
        return ResponseEntity.ok(dashboard)
    }
}