package vn.vccb.mssurveykpi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.enity.PortalDepartment;

@Repository
public interface PortalDepartmentRepository extends JpaRepository<PortalDepartment, Long> {
    PortalDepartment getByDepartmentCode(String departmentCode);
}
