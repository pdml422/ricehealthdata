package vn.edu.usth.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchStatistical {
    private String replicate;
    private String subReplicate;
    private String date;
}
