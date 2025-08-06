package ahm.parts.ordering.ui.home.dealer.competitor.adapter;


import androidx.cardview.widget.CardView;

import com.loopeer.shadow.ShadowView;

/**
 * @author Ivan Vodyasov on 20.07.2017.
 */

public interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 4;

    float getBaseElevation();
    CardView getCardViewAt(int position);
    int getCount();
}