# solution-finder

テトリスのパーフェクトの成功確率を計算するためのツールです。  
7種のミノ(ITSZJLO) の様々な組み合わせでの探索が可能です。  

現在の安定版は v0.623 となります。ダウンロードは以下のリンクからお願いします。
(GUIが入っているパッケージは、[@kitsune_fuchi](https://twitter.com/kitsune_fuchi) さん作成の補助GUIが入っています。
なお、補助GUIではpercent・path・util figコマンドのみ対応しています)  
https://github.com/knewjade/solution-finder/releases/tag/v0.623


※ プログラムの実行には、Java8が実行できる環境が必要です


もし最新版をダウンロードしたい方は、以下のリンクからお願いします。  
https://github.com/knewjade/solution-finder/releases/latest

----

# 概要

solution-finderとは、テトリスで「ある地形からパーフェクトできる確率・接着手順」など、条件に従った解を探索するためのツールです。

solution-finderは、探索ツールとして次の特徴を持っています。

* 任意のフィールド・ミノ組み合わせを指定した探索が可能
* 探索時の回転法則はSRSに準拠
* マルチスレッドによる探索に対応
* 実行時にオプションを与えることで「ホールドあり・なし」「ハードドロップのみ」など細かい設定が可能
* フィールドの入力として [連続テト譜エディタ Ver 1.15a](http://fumen.zui.jp) のデータに対応


# 主な機能

* percent: ある地形からパーフェクトできる確率を計算する
    - 7種のミノ(TIJLSZO) の様々な組み合わせでの探索が可能
    - 先頭3ミノごとのパーフェクト成功確率もツリー形式で表示
    - パーフェクトができないツモ順を表示

* path: ある地形からパーフェクトまでの操作手順をすべて列挙する
    - 指定したミノの組み合わせから、パーフェクトまでの全パターンを列挙してファイルに出力
    - 2種類の結果を列挙して出力
    - 出力フォーマットは、テト譜リンクとCSVに対応

* setup: ある地形から指定したブロックを埋める操作手順をすべて列挙する
    - 指定したミノの組み合わせから、置くことができる全パターンを列挙してファイルに出力
    - ブロックを置いても置かなくても良いマージンエリアの設定が可能

* ren(βバージョン): ある地形から最もRENが続く手順を列挙する

* util: solution-finderの機能を補助するユーティリティ
   - fig: テト譜をもとに画像を生成


# ドキュメント

詳細は、以下のドキュメントをご参照ください。

http://sfinder.s3-website-ap-northeast-1.amazonaws.com/index.html


------

This software includes the work that is distributed in the Apache License 2.0

```

Apache Commons CLI
Copyright 2001-2017 The Apache Software Foundation

This product includes software developed at
The Apache Software Foundation (http://www.apache.org/).
```

```
Apache Commons Collections
Copyright 2001-2018 The Apache Software Foundation

This product includes software developed at
The Apache Software Foundation (http://www.apache.org/).
```