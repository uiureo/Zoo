package com.example.zat.zoo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.zat.zoo.db.DbHelper;
import com.example.zat.zoo.model.Item;

public class EditActivity extends AppCompatActivity {
  private Item currentItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit);
    setItemIfNeeded();

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  void setItemIfNeeded() {
    Intent intent = getIntent();
    if (intent != null) {
      int id = intent.getIntExtra("ID", -1);

      if (id != -1) {
        DbHelper db = new DbHelper(this);
        Item item = db.find(id);

        if (item == null) return;
        currentItem = item;

        EditText nameView = (EditText) findViewById(R.id.edit_text_name);
        nameView.setText(item.name);

        EditText bodyView = (EditText) findViewById(R.id.edit_text_body);
        bodyView.setText(item.body);
      }
    }
  }

  Item Save(String name, String body) {
    DbHelper db = new DbHelper(this);

    if (currentItem != null) {
      currentItem.name = name;
      currentItem.body = body;
      db.insert(currentItem);

      return currentItem;
    }

    Item item = new Item(name, body);
    db.insert(item);

    return item;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_save) {
      EditText nameView = (EditText) findViewById(R.id.edit_text_name);
      String name = nameView.getText().toString();

      EditText bodyView = (EditText) findViewById(R.id.edit_text_body);
      String body = bodyView.getText().toString();

      Save(name, body);

      Intent intent = new Intent(this, ListActivity.class);
      navigateUpTo(intent);

      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.edit, menu);
    return true;
  }
}
