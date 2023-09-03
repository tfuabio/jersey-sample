package com.sample.jersey.app;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {
  private Person person = new Person();

  @Getter
  @ToString
  private static class Person {
    private String name = "Hanako Yamada";
    private Integer age = 31;
  }

  private static Config config;

  public static synchronized void loadFromFile(Path path) {
    if (config == null) {
      // Initiallize.
      config = new Config();

      // Load Config File.
      try (Reader reader = Files.newBufferedReader(path)) {
        config = new Gson().fromJson(reader, Config.class);
      } catch (IOException e) {
        // TODO: handle exception
      }
    }
  }

  public static Config get() {
    return config;
  }

  // 動作確認用
  public static void main(String[] args) {
    Config.loadFromFile(Paths.get("./config.json"));
    System.out.println(Config.get());
  }
}
