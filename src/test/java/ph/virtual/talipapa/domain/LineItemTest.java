package ph.virtual.talipapa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ph.virtual.talipapa.web.rest.TestUtil;

public class LineItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineItem.class);
        LineItem lineItem1 = new LineItem();
        lineItem1.setId(1L);
        LineItem lineItem2 = new LineItem();
        lineItem2.setId(lineItem1.getId());
        assertThat(lineItem1).isEqualTo(lineItem2);
        lineItem2.setId(2L);
        assertThat(lineItem1).isNotEqualTo(lineItem2);
        lineItem1.setId(null);
        assertThat(lineItem1).isNotEqualTo(lineItem2);
    }
}
