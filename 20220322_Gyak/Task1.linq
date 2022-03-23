<Query Kind="Statements">
  <Connection>
    <ID>54bf9502-9daf-4093-88e8-7177c12aaaaa</ID>
    <NamingService>2</NamingService>
    <Persist>true</Persist>
    <Driver Assembly="(internal)" PublicKeyToken="no-strong-name">LINQPad.Drivers.EFCore.DynamicDriver</Driver>
    <AttachFileName>&lt;ApplicationData&gt;\LINQPad\ChinookDemoDb.sqlite</AttachFileName>
    <DisplayName>Demo database (SQLite)</DisplayName>
    <DriverData>
      <PreserveNumeric1>true</PreserveNumeric1>
      <EFProvider>Microsoft.EntityFrameworkCore.Sqlite</EFProvider>
      <MapSQLiteDateTimes>true</MapSQLiteDateTimes>
      <MapSQLiteBooleans>true</MapSQLiteBooleans>
    </DriverData>
  </Connection>
</Query>

var source = new [] {
   "Bolyki Balazs",
   "Toronya Bertalan",
   "Veres Marcell",
   "Polonkai Dávid",
   "Németh Viktor"
};

//var res = source.Take(3);

//var res = source.OrderByDescending(name => name);

//var res = source.Where(name => name.Contains('i') || name.Contains('l'));
//source.Aggregate(0, (total, next) => total += (next.Contains('i') || next.Contains('l')) ? 1 : 0).Dump();

source.Where(name => name.Split(' ')[0].Length >= 6).Dump();

//Console.WriteLine(res);
//foreach (var resI in res) {
//	Console.WriteLine(resI);
//}