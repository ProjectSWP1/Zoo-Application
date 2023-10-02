package com.thezookaycompany.zookayproject.repositories;
import com.thezookaycompany.zookayproject.model.entity.ZooArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZooAreaRepository extends JpaRepository<ZooArea , String > {
    ZooArea findZooAreaByZooAreaId (String zooAreaId);
}
