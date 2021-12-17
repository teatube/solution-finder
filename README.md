# solution-finder README.md 半机翻
[原项目](https://github.com/knewjade/solution-finder)

<!-- テトリスの「パーフェクトクリア」「REN/Combo」「T-Spin」の手順などを探すためのツールです。  
探索条件には、7種のミノ(ITSZJLO) の組み合わせや地形を自由に設定できます。 -->
这是用于查找俄罗斯方块的「完美清除 / Perfect Clear」「连击 / REN / Combo」「T型回旋 / T-Spin」的步骤等各种功能的工具。
搜索条件允许您自由设置7种方块(ITSZJLO)组合和地形。

<!-- 最新版は、以下のリンクからダウンロードできます。  
https://github.com/knewjade/solution-finder/releases/latest -->
您可以从以下链接下载最新版本。
https://github.com/knewjade/solution-finder/releases/latest


<!-- (GUIが入っているパッケージには、[@kitsune_fuchi](https://twitter.com/kitsune_fuchi) さん作成の補助GUIが入っています。 なお、補助GUIではpercent・path・util
figコマンドのみ対応しています) -->
拥有图形界面（GUI）的版本当中，含有 [@kitsune_fuchi](https://twitter.com/kitsune_fuchi) 桑做的辅助GUI。另外，辅助GUI只支持percent·path·util fig 命令。

<!-- ※ プログラムの実行には、[Java8](https://www.java.com/ja/download/) 以降を実行できる環境が必要です -->
※ 运行此程序需要 [Java8](https://www.java.com/ja/download/) 或之后版本的环境。

----

<!-- # 概要 -->
# 概要

<!-- solution-finderでは、次のような結果を得ることができます。

* ある地形からパーフェクトクリアできる確率を計算
* ある地形からパーフェクトクリアになるミノの置き方
* RENが最も続くようなミノの置き方
* T-Spinできるようになるミノの置き方
* 指定した地形と同じブロックの配置になるミノの置き方 -->

在solution-finder中，您可以获得以下结果：

* 计算从某个地形可以完美清除的概率
* 从某个地形可以完美清除的摆块方法
* 最长连击的摆块方法
* 能够T-Spin摆块方法。
* 与指定地形配置相同块的放块方法

solution-finderは、探索ツールとして次の特徴を持っています。

* 任意のフィールド・ミノ組み合わせを指定した探索が可能
* 探索時の回転法則はSRSに準拠
* マルチスレッドによる探索に対応
* 実行時にオプションを与えることで「ホールドあり・なし」「ハードドロップのみ」など細かい設定が可能
* フィールドの入力として [連続テト譜エディタ Ver 1.15a](http://fumen.zui.jp) のデータに対応

solution-finder 作为搜索工具，具有以下功能：

* 可搜索指定任意地形·任意的方块序列组合
* 搜索时的旋转系统遵循SRS
* 支持多线程搜索
* 通过在运行时给出选项，可以进行「HOLD有/无」「仅限硬降」等精细设置。
* 场地地形使用[连续方块谱面(fumen)编辑器Ver1.15a（点此导向茶服链接）](https://teatube.ltd/f/)的数据（fumen码）输入

<!-- # 主な機能

* percent: ある地形からパーフェクトクリアできる確率を計算する
    - 7種のミノ(TIJLSZO) の様々な組み合わせでの探索が可能
    - 先頭nミノごとのパーフェクト成功確率もツリー形式で表示
    - パーフェクトができないツモ順を表示

* path: ある地形からパーフェクトまでの操作手順をすべて列挙する
    - 指定したミノの組み合わせから、パーフェクトまでの全パターンを列挙してファイルに出力
    - 2種類の結果を列挙して出力
    - 出力フォーマットは、テト譜リンクとCSVに対応

* setup: ある地形から指定したブロックを埋める操作手順をすべて列挙する
    - 指定したミノの組み合わせから、置くことができる全パターンを列挙してファイルに出力
    - ブロックを置いても置かなくても良いマージンエリアの設定が可能

* ren: ある地形からRENが続く手順を列挙する
  - 指定したミノ順から、RENを列挙してファイルに出力

* spin: ある地形からTスピンできる手順を列挙する

* cover: 指定したミノの置き場所通りに置くことができるミノ順をすべて列挙する

* util: solution-finderの機能を補助するユーティリティ
  - fig: テト譜をもとに画像を生成
  - fumen: 入力されたテト譜を変換して、新たなテト譜を標準出力に出力
  - seq: 入力されたパターンの``*``などをシーケンス（ツモ順）に展開 -->

# 主要功能

* percent：计算从某个地形可以完美清除的概率
    - 可以在7种方块(TIJLSZO)的各种组合中进行探索
    - 最开始 n 个方块的完美成功概率也以树形式表示
    - 显示无法完美全清的方块序列（自摸顺）

* path：列举所有从某个地形到完美的操作步骤
    - 列出从指定的方块组合到完美全清的所有方式（pattern），并将其输出到文件
    - 列出两种结果并输出
    - 输出格式支持fumen谱面链接和CSV

* setup：列出从某个地形填充指定块的所有操作步骤
    - 从指定的方块组合中列出可以放置的所有方式（pattern），并将其输出到文件
    - 可以设置 某处是否放块 的临界区域

* ren：列举从某个地形持续连击消除的步骤
    - 从指定的方块顺序列出连击方法并将其输出到文件

* spin：列举从某个地形可以T旋的步骤

* cover：列出所有可以放置在指定方块的放置地点上的方块序列（自摸顺）

* util：帮助solution-finder功能的实用程序
    - fig：根据fumen谱面生成图像。
    - fumen：转换输入方块谱面，并以新的方块谱面标准输出
    - seq：将输入模式的``*``等扩展为序列(自摸顺)

<!-- # ドキュメント
 -->
# 文档

<!-- 詳細は、以下のドキュメントをご参照ください。 -->
更多详情请参考以下文档。（日文）

https://knewjade.github.io/sfinder-docs


------

This software includes the work that is distributed in the Apache License 2.0

```
Apache Commons CLI
Copyright 2001-2017 The Apache Software Foundation

This product includes software developed at
The Apache Software Foundation (http://www.apache.org/).
```
