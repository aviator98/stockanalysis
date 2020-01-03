package cn.stockAnalysis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.stockAnalysis.domain.Company;

public interface CompanyRepository extends JpaRepository<Company, String> {

	@Modifying
	@Query("update Company c set c.importStatus = :importStatus")
	public int updateImportStatus(@Param("importStatus")int importStatus);
}