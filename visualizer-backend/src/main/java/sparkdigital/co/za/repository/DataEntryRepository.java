package sparkdigital.co.za.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;

import sparkdigital.co.za.model.DataEntry;

public interface DataEntryRepository extends JpaRepository<DataEntry, Long> {
}
