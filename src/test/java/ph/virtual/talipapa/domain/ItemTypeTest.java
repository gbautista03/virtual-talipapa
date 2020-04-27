package ph.virtual.talipapa.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ph.virtual.talipapa.web.rest.TestUtil;

public class ItemTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemType.class);
        ItemType itemType1 = new ItemType();
        itemType1.setId(1L);
        ItemType itemType2 = new ItemType();
        itemType2.setId(itemType1.getId());
        assertThat(itemType1).isEqualTo(itemType2);
        itemType2.setId(2L);
        assertThat(itemType1).isNotEqualTo(itemType2);
        itemType1.setId(null);
        assertThat(itemType1).isNotEqualTo(itemType2);
    }
}
