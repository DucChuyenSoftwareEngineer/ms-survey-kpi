package vn.vccb.mssurveykpi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.vccb.mssurveykpi.enity.PortalUser;

@Repository
public interface PortalUserRepository extends JpaRepository<PortalUser, Long> {
    PortalUser getByUsername(String username);
}
