package interview.wbox.atm.repository;

import interview.wbox.atm.model.Banknote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by ioana on 11/28/2020.
 */
@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Long>{

    @Query("select b from Banknote b where b.value = :value")
    Banknote findByValue(@Param("value") Integer value) ;
}