package interview.wbox.atm.repository;

import interview.wbox.atm.model.Banknote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ioana on 11/28/2020.
 */
@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Long>{

    Banknote findByValue(Integer value) ;

    @Query("select b from Banknote b order by b.value desc")
    List<Banknote> findAllDesc() ;
}
