/**
 Скрипт создания таблицы и её наполнения тестовыми данными
 */

CREATE TABLE marker (
  id        SERIAL                 NOT NULL,
  name      CHARACTER VARYING(255) NOT NULL,
  latitude  REAL                   NOT NULL,
  longitude REAL                   NOT NULL,
  CONSTRAINT marker_pkey PRIMARY KEY (id)
);

INSERT INTO marker VALUES
  (81, 'Турция', 37.070374, 39.550781),
  (82, 'Швеция', 59.03196, 13.625511),
  (83, 'Мой дом', 81.089358, -16.523438),
  (84, 'Дом моей бабушки', 65.502639, -16.171875),
  (85, 'Лондон', 51.507351, -0.127758),
  (86, 'Курорт', 62.754726, -91.40625),
  (87, 'Тёплые пески', 22.796439, 16.787109);
