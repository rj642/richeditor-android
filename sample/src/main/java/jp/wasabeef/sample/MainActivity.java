package jp.wasabeef.sample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jp.wasabeef.richeditor.RichEditor;
import jp.wasabeef.richeditor.WriteCustomButton;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

  private RichEditor mEditor;
  private TextView mPreview;
  private ArrayList<WriteCustomButton> Buttons;
  private WriteCustomButton textBoldButton;
  private WriteCustomButton textQuoteButton;
  private WriteCustomButton textItalicButton;
  private WriteCustomButton textTodoButton;
  private WriteCustomButton textCodeButton;
  private WriteCustomButton textStrikeButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mEditor = (RichEditor) findViewById(R.id.editor);
    mEditor.setEditorHeight(200);
    mEditor.setEditorFontSize(22);
    mEditor.setEditorFontColor(Color.BLACK);
    //mEditor.setEditorBackgroundColor(Color.BLUE);
    //mEditor.setBackgroundColor(Color.BLUE);
    //mEditor.setBackgroundResource(R.drawable.bg);
    mEditor.setPadding(10, 10, 10, 10);
    //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
    mEditor.setPlaceholder("Insert text here...");
    //mEditor.setInputEnabled(false);

    mPreview = (TextView) findViewById(R.id.preview);
    mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
      @Override
      public void onTextChange(String text) {
        mPreview.setText(text);
      }
    });

    class DecorationButtonListener implements View.OnClickListener {

      @Override
      public void onClick(View view){
        if(view instanceof WriteCustomButton) {
          WriteCustomButton button = (WriteCustomButton) view;

          mEditor.clearAndFocusEditor();
          if(button.getId() == R.id.action_bold) {
            mEditor.setBold();
            if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
              //mEditor.activeStyle = "bold";
            } else {
              mEditor.activeStyle = "bold";
            }
          }
          else if (button.getId() == R.id.action_blockquote) {
            mEditor.setBlockquote();
            mEditor.activeStyle = "quote";
          }
          else if (button.getId() == R.id.action_italic) {
            mEditor.setItalic();
            if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
              //mEditor.activeStyle = "bold";
            } else {
              mEditor.activeStyle = "italic";
            }
          }
          else if (button.getId() == R.id.action_strikethrough) {
            mEditor.setStrikeThrough();
            if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
              //mEditor.activeStyle = "bold";
            } else {
              mEditor.activeStyle = "strike";
            }
          }
          else if (button.getId() == R.id.action_insert_checkbox) {
            mEditor.insertTodo();
            mEditor.activeStyle = "todo";
          }
          else if (button.getId() == R.id.action_insert_link) {
            mEditor.setCodeBlock();
            mEditor.activeStyle = "code";
          }
          if(button.getCheckedState()) {
            button.setColorFilter(Color.WHITE);
            button.switchCheckedState();
            mEditor.activeStyle = "none";
          }
          else {
            button.setColorFilter(Color.BLUE);
            button.switchCheckedState();
            switch (button.getId()) {
              case R.id.action_bold:
                if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
                  //mEditor.activeStyle = "bold";
                } else {
                  mEditor.activeStyle = "bold";
                }
                break;
              case R.id.action_italic:
                if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
                  //mEditor.activeStyle = "bold";
                } else {
                  mEditor.activeStyle = "italic";
                }
                break;
              case R.id.action_strikethrough:
                if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
                  //mEditor.activeStyle = "bold";
                } else {
                  mEditor.activeStyle = "strike";
                }
                break;
              case R.id.action_blockquote:
                mEditor.activeStyle = "quote";
                break;
              case R.id.action_insert_link:
                mEditor.activeStyle = "code";
                break;
              case R.id.action_insert_checkbox:
                mEditor.activeStyle = "todo";
                break;
            }
          }

        }
      }
    }

    mEditor.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
      @Override public void onStateChangeListener(String text, List<RichEditor.Type> types) {
        Buttons = new ArrayList<>(
          Arrays.asList(textBoldButton, textQuoteButton, textItalicButton, textTodoButton, textStrikeButton));
        for(RichEditor.Type type : types) {
          switch(type) {
            case BOLD:
              textBoldButton.setColorFilter(Color.BLUE);
              if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
                //mEditor.activeStyle = "bold";
              } else {
                mEditor.activeStyle = "bold";
              }
              if(!textBoldButton.getCheckedState()) {
                textBoldButton.switchCheckedState();
              }
              Buttons.remove(textBoldButton);
              break;
            case QUOTEBLOCK:
              textQuoteButton.setColorFilter(Color.BLUE);
              if(!textQuoteButton.getCheckedState()) {
                textQuoteButton.switchCheckedState();
                mEditor.activeStyle = "quote";
              }
              Buttons.remove(textQuoteButton);
              break;
            case ITALIC:
              textItalicButton.setColorFilter(Color.BLUE);
              if (!textItalicButton.getCheckedState()) {
                textItalicButton.switchCheckedState();
                if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
                  //mEditor.activeStyle = "bold";
                } else {
                  mEditor.activeStyle = "italic";
                }
              }
              Buttons.remove(textItalicButton);
              break;
            case TODO:
              textTodoButton.setColorFilter(Color.BLUE);
              if (!textTodoButton.getCheckedState()) {
                textTodoButton.switchCheckedState();
                mEditor.activeStyle = "todo";
              }
              Buttons.remove(textTodoButton);
              break;
            case CODEBLOCK:
              textCodeButton.setColorFilter(Color.BLUE);
              if (!textCodeButton.getCheckedState()) {
                textCodeButton.switchCheckedState();
                mEditor.activeStyle = "code";
              }
              Buttons.remove(textCodeButton);
              break;
            case STRIKETHROUGH:
              textStrikeButton.setColorFilter(Color.BLUE);
              if (!textStrikeButton.getCheckedState()) {
                textStrikeButton.switchCheckedState();
                if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
                  //mEditor.activeStyle = "bold";
                } else {
                  mEditor.activeStyle = "strike";
                }
              }
              Buttons.remove(textStrikeButton);
              break;
          }
        }
        for(WriteCustomButton button : Buttons){
          button.setColorFilter(Color.WHITE);
          button.setCheckedState(false);
          switch (button.getId()) {
            case R.id.action_bold:
              if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
                //mEditor.activeStyle = "bold";
              } else {
                mEditor.activeStyle = "bold";
              }
              break;
            case R.id.action_italic:
              if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
                //mEditor.activeStyle = "bold";
              } else {
                mEditor.activeStyle = "italic";
              }
              break;
            case R.id.action_strikethrough:
              if (mEditor.activeStyle.equals("quote") || mEditor.activeStyle.equals("todo") || mEditor.activeStyle.equals("code")) {
                //mEditor.activeStyle = "bold";
              } else {
                mEditor.activeStyle = "strike";
              }
              break;
            case R.id.action_blockquote:
              mEditor.activeStyle = "quote";
              break;
            case R.id.action_insert_link:
              mEditor.activeStyle = "code";
              break;
            case R.id.action_insert_checkbox:
              mEditor.activeStyle = "todo";
              break;
            default:
              mEditor.activeStyle = "none";
              break;
          }
        }
      }
    });

    DecorationButtonListener decorationButtonListener = new DecorationButtonListener();

    findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.undo();
      }
    });

    findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.redo();
      }
    });

    //findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
    //  @Override
    //  public void onClick(View v) {
    //    mEditor.setBold();
    //  }
    //});

    textBoldButton = findViewById(R.id.action_bold);

    textBoldButton.setOnClickListener(decorationButtonListener);

    textQuoteButton = findViewById(R.id.action_blockquote);

    textQuoteButton.setOnClickListener(decorationButtonListener);

    textItalicButton = findViewById(R.id.action_italic);

    textItalicButton.setOnClickListener(decorationButtonListener);

    textTodoButton = findViewById(R.id.action_insert_checkbox);

    textTodoButton.setOnClickListener(decorationButtonListener);

    textCodeButton = findViewById(R.id.action_insert_link);

    textCodeButton.setOnClickListener(decorationButtonListener);

    textStrikeButton = findViewById(R.id.action_strikethrough);

    textStrikeButton.setOnClickListener(decorationButtonListener);

    /*findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setItalic();
      }
    });*/

    findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setSubscript();
      }
    });

    findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setSuperscript();
      }
    });

    /*findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setStrikeThrough();
      }
    });*/

    findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setUnderline();
      }
    });

    findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setHeading(1);
      }
    });

    findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setHeading(2);
      }
    });

    findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setHeading(3);
      }
    });

    findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setHeading(4);
      }
    });

    findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setHeading(5);
      }
    });

    findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setHeading(6);
      }
    });

    findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
      private boolean isChanged;

      @Override
      public void onClick(View v) {
        mEditor.setTextColor(isChanged ? Color.BLACK : Color.RED);
        isChanged = !isChanged;
      }
    });

    findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
      private boolean isChanged;

      @Override
      public void onClick(View v) {
        mEditor.setTextBackgroundColor(isChanged ? Color.TRANSPARENT : Color.YELLOW);
        isChanged = !isChanged;
      }
    });

    findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setIndent();
      }
    });

    findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setOutdent();
      }
    });

    findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setAlignLeft();
      }
    });

    findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setAlignCenter();
      }
    });

    findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setAlignRight();
      }
    });

    /*findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setBlockquote();
      }
    });*/

    findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setBullets();
      }
    });

    findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.setNumbers();
      }
    });

    findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.insertImage("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg",
          "dachshund", 320);
      }
    });

    //findViewById(R.id.action_insert_youtube).setOnClickListener(new View.OnClickListener() {
    //  @Override
    //  public void onClick(View v) {
    //    mEditor.insertYoutubeVideo("https://www.youtube.com/embed/pS5peqApgUA");
    //  }
    //});
    //
    //findViewById(R.id.action_insert_audio).setOnClickListener(new View.OnClickListener() {
    //  @Override
    //  public void onClick(View v) {
    //    mEditor.insertAudio("https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_5MG.mp3");
    //  }
    //});
    //
    //findViewById(R.id.action_insert_video).setOnClickListener(new View.OnClickListener() {
    //  @Override
    //  public void onClick(View v) {
    //    mEditor.insertVideo("https://test-videos.co.uk/vids/bigbuckbunny/mp4/h264/1080/Big_Buck_Bunny_1080_10s_10MB.mp4", 360);
    //  }
    //});

    /*findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.insertLink("https://github.com/wasabeef", "wasabeef");
      }
    });*/
    /*findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mEditor.insertTodo();
      }
    });*/
  }
}
