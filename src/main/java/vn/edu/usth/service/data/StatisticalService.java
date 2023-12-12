package vn.edu.usth.service.data;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import vn.edu.usth.model.Statistical;
import vn.edu.usth.payload.SearchStatistical;
import vn.edu.usth.repository.StatisticalRepository;

import java.util.List;

@ApplicationScoped
public class StatisticalService {
    private final StatisticalRepository statisticalRepository;
    @Inject
    public StatisticalService(StatisticalRepository statisticalRepository) {
        this.statisticalRepository = statisticalRepository;
    }

    public List<Statistical> search(SearchStatistical searchStatistical) {
        return statisticalRepository.search(searchStatistical);
    }
}
