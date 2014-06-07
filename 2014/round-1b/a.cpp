#include <cassert>
#include <iostream>
#include <string>
#include <vector>

using namespace std;
using Frequency = pair<int, int>;
using FrequencyString = vector<Frequency>;

int Solve(const vector<string>&);
vector<FrequencyString> MakeFrequencyStrings(const vector<string>&);
int GetCost(const vector<FrequencyString>&);

int main() {
  int t;
  cin >> t;

  for (int i = 1; i <= t; ++i) {
    int n;
    cin >> n;
    cin.ignore();

    vector<string> vs;
    vs.reserve(n);

    for (int j = 0; j < n; ++j) {
      string s;
      getline(cin, s);

      vs.emplace_back(s);
    }

    long num = Solve(vs);

    cout << "Case #" << i << ": ";

    if (num < 0)
      cout << "Fegla Won";
    else
      cout << num;

    cout << endl;
  }

  return 0;
}

int Solve(const vector<string>& vs) {
  assert(vs.size() >= 2);

  return GetCost(MakeFrequencyStrings(vs));
}

int GetCost(const vector<FrequencyString>& frequency_strings) {
  if (frequency_strings.empty()) return -1;

  const int num_chunks = frequency_strings[0].size();
  const int size = frequency_strings.size();
  const int middle_index = size / 2;

  int cost = 0;
  vector<int> frequencies;
  frequencies.reserve(size);

  for (int i = 0; i < num_chunks; ++i) {
    for (const auto& frequency_string : frequency_strings)
      frequencies.emplace_back(frequency_string[i].second);

    nth_element(begin(frequencies), begin(frequencies) + middle_index,
                end(frequencies));

    const int median = frequencies[middle_index];

    for (const auto& i : frequencies)
      cost += abs(i - median);

    frequencies.clear();
  }

  return cost;
}

vector<FrequencyString> MakeFrequencyStrings(const vector<string>& vs) {
  vector<FrequencyString> frequency_strings;
  frequency_strings.reserve(vs.size());

  FrequencyString frequency_string;

  for (const auto& s : vs) {
    if (!frequency_strings.empty())
      frequency_string.reserve(frequency_strings.back().size());

    for (int i = 0; i < s.length();) {
      int count = 1;
      int j;

      for (j = i + 1; j < s.length() && s[j] == s[i]; ++j, ++count) {}

      frequency_string.emplace_back(Frequency(s[i], count));
      i = j;
    }

    if (!frequency_strings.empty()) {
      const FrequencyString& other_frequency_string = frequency_strings.back();

      if (frequency_string.size() != other_frequency_string.size())
        return vector<FrequencyString>();

      for (int i = 0; i < other_frequency_string.size(); ++i)
        if (frequency_string[i].first != other_frequency_string[i].first)
          return vector<FrequencyString>();
    }

    frequency_strings.emplace_back(frequency_string);
    frequency_string.clear();
  }

  return frequency_strings;
}
