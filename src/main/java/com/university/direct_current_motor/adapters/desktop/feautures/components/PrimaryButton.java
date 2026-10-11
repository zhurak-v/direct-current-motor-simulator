package com.university.direct_current_motor.adapters.desktop.feautures.components;

import com.university.direct_current_motor.adapters.desktop.common.components.Box;
import com.university.direct_current_motor.adapters.desktop.common.components.Button;

public final class PrimaryButton extends Button {

   public PrimaryButton() {
       super();
       this.applyPrimaryStyles();
   }

   private void applyPrimaryStyles() {
       this.addInlineStyles("""
                -fx-background-color: #ffffff;
                -fx-background-radius: 12;
                -fx-border-radius: 12;
        """
       );
       this.onHover(
               (evt, btn) -> {
                   btn.addInlineStyles("""
                        -fx-background-color: #f5f5f5;
                    """);
               },
               (evt, btn) -> {
                   btn.removeInlineStyles("""
                        -fx-background-color: #f5f5f5;
                    """);
               }
       );
       this.setAlign(Box.Align.CENTER, Box.Align.CENTER);
   }

}
