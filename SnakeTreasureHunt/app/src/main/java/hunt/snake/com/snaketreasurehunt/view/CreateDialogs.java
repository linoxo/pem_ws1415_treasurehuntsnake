package hunt.snake.com.snaketreasurehunt.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import hunt.snake.com.snaketreasurehunt.R;

/**
 * Created by lino on 30.11.14.
 */
public class CreateDialogs {

    private Context context;

    public CreateDialogs(Context context) {
       this.context = context;
    }


    public void createPauseScreen() {
        final Dialog dialog = new Dialog(context,android.R.style.Theme_Holo_Light_DarkActionBar);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(150);
        dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.game_paused_dialog);
        Button resume = (Button)dialog.findViewById(R.id.resumeButton);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Button restart = (Button)dialog.findViewById(R.id.restartButton);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });
        Button quit = (Button)dialog.findViewById(R.id.quitButton);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent();
                intent.setClassName(context.getPackageName(), context.getPackageName()+".view.StartScreenMainActivity");
                context.startActivity(intent);
            }
        });

        dialog.show();
    }

    public void createGameOverScreen() {
        final Dialog dialog = new Dialog(context,android.R.style.Theme_Holo_Light_DarkActionBar);
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(150);
        dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.game_over_dialog);

        Button restart = (Button)dialog.findViewById(R.id.restartButton);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });
        Button quit = (Button)dialog.findViewById(R.id.quitButton);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(context.getPackageName(), context.getPackageName()+".view.StartScreenMainActivity");
                context.startActivity(intent);
                dialog.cancel();

            }
        });

        dialog.show();
    }
}
