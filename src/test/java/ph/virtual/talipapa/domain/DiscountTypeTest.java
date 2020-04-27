package ph.virtual.talipapa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ph.virtual.talipapa.web.rest.TestUtil;

public class DiscountTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscountType.class);
        DiscountType discountType1 = new DiscountType();
        discountType1.setId(1L);
        DiscountType discountType2 = new DiscountType();
        discountType2.setId(discountType1.getId());
        assertThat(discountType1).isEqualTo(discountType2);
        discountType2.setId(2L);
        assertThat(discountType1).isNotEqualTo(discountType2);
        discountType1.setId(null);
        assertThat(discountType1).isNotEqualTo(discountType2);
    }
}
